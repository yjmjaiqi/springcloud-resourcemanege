package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value ="note_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteType {
    private Integer id;
    private String noteTypeName;
    private Integer userId;
}
