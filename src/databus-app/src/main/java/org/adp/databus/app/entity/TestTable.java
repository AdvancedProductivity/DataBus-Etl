package org.adp.databus.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.adp.databus.app.mapper.handler.SqliteDateHandler;

import java.util.Date;

/**
 * used for test database init
 *
 * @author zzq
 */
public class TestTable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private int type;

    @TableField(value = "created_at", typeHandler = SqliteDateHandler.class)
    private Date dateCreated;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "TestTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
