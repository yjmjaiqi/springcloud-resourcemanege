package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value ="video_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoType {
    private Integer id;
    private String videoTypeName;
    private Integer userId;
}
