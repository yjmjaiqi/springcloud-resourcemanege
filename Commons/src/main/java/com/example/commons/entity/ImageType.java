package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value ="img_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageType {
    private Integer id;
    private String imgTypeName;
    private Integer userId;
}
