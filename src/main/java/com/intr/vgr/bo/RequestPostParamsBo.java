package com.intr.vgr.bo;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostParamsBo {
    private Optional<Integer> category;
    private Optional<Integer> elementsLength;
    private Optional<Integer> page;

}
