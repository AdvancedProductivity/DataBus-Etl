package org.adp.databus.app.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.adp.databus.app.entity.TestTable;
import org.adp.databus.app.mapper.TestTableMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PrepareWorkTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Resource
    private DataSource dataSource;

    @Resource
    private DataBusConst dataBusConst;

    @Resource
    private TestTableMapper testTableMapper;


    private static Logger logger = LoggerFactory.getLogger(PrepareWorkTest.class);

    @Test
    public void testDataBusConst() {
        assertNotNull(dataBusConst);
        assertNotNull(dataBusConst.pluginRespFolderDefine);
        assertNotNull(DataBusConst.USER_DIR);
        assertTrue(StringUtils.isNotEmpty(DataBusConst.USER_DIR));
        assertTrue(StringUtils.isNotEmpty(dataBusConst.pluginRespFolderDefine));
    }

    @Test
    public void testEvent() {
        String userDirectoryPath = DataBusConst.USER_DIR;
        File databaseFile = FileUtils.getFile(
                userDirectoryPath,
                dataBusConst.applicationName,
                dataBusConst.dataBusFileName
        );
        assertNotNull(databaseFile);
        assertTrue(databaseFile.exists() && databaseFile.isFile());
    }

    @Test
    public void testCreatedTable() {
        assertNotNull(dataSource);
        assertDoesNotThrow(() -> {
            final Connection connection = dataSource.getConnection();
            final Statement statement = connection.createStatement();
            String testStr = RandomStringUtils.random(10, true, false);
            int c = RandomUtils.nextInt(1, 10000);
            String sql = "INSERT INTO TEST_TABLE (name, type) values ('" + testStr + "', " + c + ") ";
            final int insertCount = statement.executeUpdate(sql);
            logger.info("execute sql:{} result: {}", sql, insertCount);
            String selectSql = "SELECT COUNT(1) FROM TEST_TABLE WHERE name = '" + testStr + "' and type = " + c;
            final ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                final int anInt = resultSet.getInt(1);
                logger.info("execute sql: {} result: {}", selectSql, anInt);
                assertTrue(anInt > 0);
            }
            statement.close();
            connection.close();
        });
    }

    @Test
    public void testMapper() {
        assertDoesNotThrow(() -> {
            TestTable table = new TestTable();

            String testStr = RandomStringUtils.random(10, true, false);
            int c = RandomUtils.nextInt(1, 10000);
            table.setName(testStr);
            table.setType(c);
            Date now = new Date();
            table.setDateCreated(now);
            logger.info("add data to test table: {}", table);

            final int insert = testTableMapper.insert(table);
            assertEquals(1, insert);
            final List<TestTable> testTables = testTableMapper.selectList(new QueryWrapper<TestTable>()
                    .lambda()
                    .eq(TestTable::getName, testStr)
                    .eq(TestTable::getDateCreated, now)
                    .eq(TestTable::getType, table.getType())
            );
            assertEquals(1, testTables.size());
            assertEquals(now.getTime(), testTables.get(0).getDateCreated().getTime());
            logger.info("see data: {}", testTables.get(0).toString());
        });
    }

    /**
     * test mybatis order
     */
    @Test
    public void testOrderBy() {
        assertDoesNotThrow(() -> {
            String nameA = RandomStringUtils.random(10, true, false);
            String nameB = RandomStringUtils.random(10, true, false);
            int c = RandomUtils.nextInt(1, 10000);
            TestTable dataA = new TestTable();
            dataA.setName(nameA);
            dataA.setType(c);
            dataA.setDateCreated(new Date(System.currentTimeMillis() - 10 * 60 * 1000));


            TestTable dataB = new TestTable();
            dataB.setName(nameB);
            dataB.setType(c);
            dataB.setDateCreated(new Date());

            final int dataAInsert = testTableMapper.insert(dataA);
            assertEquals(1, dataAInsert);

            final int dataBInsert = testTableMapper.insert(dataB);
            assertEquals(1, dataBInsert);

            List<TestTable> testTables = testTableMapper.selectList(new QueryWrapper<TestTable>()
                    .lambda()
                    .in(TestTable::getName, Arrays.asList(nameA, nameB))
                    .orderByAsc(TestTable::getDateCreated)
            );
            logger.info("SELECT * FROM TEST_TABLE WHERE NAME IN ('{}', '{}')", nameA, nameB);
            assertEquals(testTables.size(), 2);
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            logger.info("date from {} to {}", s.format(testTables.get(0).getDateCreated()), s.format(testTables.get(1).getDateCreated()));
            assertEquals(nameA, testTables.get(0).getName());
            assertEquals(nameB, testTables.get(1).getName());


            testTables = testTableMapper.selectList(new QueryWrapper<TestTable>()
                    .lambda()
                    .in(TestTable::getName, Arrays.asList(nameA, nameB))
                    .orderByDesc(TestTable::getDateCreated)
            );
            assertEquals(testTables.size(), 2);
            logger.info("date from {} to {}", s.format(testTables.get(0).getDateCreated()), s.format(testTables.get(1).getDateCreated()));
            assertEquals(nameB, testTables.get(0).getName());
            assertEquals(nameA, testTables.get(1).getName());

        });
    }

    @After
    public void clearData() {
        final int delete = testTableMapper.delete(new QueryWrapper<TestTable>().lambda().apply("1=1"));
        logger.info("delete data count: {}", delete);
    }
}