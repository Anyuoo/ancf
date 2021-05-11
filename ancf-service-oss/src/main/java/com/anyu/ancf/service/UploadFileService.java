package com.anyu.ancf.service;

import com.anyu.ancf.mapper.UploadFileMapper;
import com.anyu.common.model.entity.UploadFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
*用户上传文件记录服务
* @author Anyu
* @since 2021/5/11
*/
@Service
public class UploadFileService extends ServiceImpl<UploadFileMapper, UploadFile> implements IService<UploadFile> {

}
