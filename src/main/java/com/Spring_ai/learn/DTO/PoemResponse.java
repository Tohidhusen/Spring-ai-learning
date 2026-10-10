package com.Spring_ai.learn.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PoemResponse {
    private String title;
    private String poemText;
    private String rhyme_scheme;
}
