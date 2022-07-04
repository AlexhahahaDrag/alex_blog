package com.alex.blog.id.entity;

import com.codingapi.leaf.framework.LeafIdGenerate;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Demo implements LeafIdGenerate {

    private Long id;

    private String code;

    private String value;

    public Demo() {
        this.id = this.generateLongId();
    }
}
