package com.anyu.common.model.entity;

import com.anyu.common.model.BaseEntity;
import com.anyu.common.model.enums.FileType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
*用户上传文件信息
* @author Anyu
* @since 2021/5/11
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("upload_file")
public class UploadFile extends BaseEntity {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 文件类型
     */
    private FileType type;

    /**
     *oss 文件夹
     */
    private String folder;
    /**
     * OSS资源位置
     */
    private String url;
}
