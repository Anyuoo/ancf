package com.anyu.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseEntity implements Serializable {
    /**
     * 0-正常，1-已删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    protected Integer status;

    /**
     * 信息创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;
    /**
     * 信息修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime modifiedTime;
}
