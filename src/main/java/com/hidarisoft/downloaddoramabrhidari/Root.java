package com.hidarisoft.downloaddoramabrhidari;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Root {
    private String thumbnail;
    private String iframe_id;
    private boolean allow_resize;
    private ArrayList<Stream> streams;
}
