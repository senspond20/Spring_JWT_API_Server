package com.sens.pot.service.board.dto;

import java.time.LocalDateTime;

import com.sens.pot.model.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class BoardListResponseDto {
    private Long id;
    private String title;  
    private int liked;  
    private boolean isActive;
    // private String categoryName; 
    // private Category category; 
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
