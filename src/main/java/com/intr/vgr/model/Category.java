package com.intr.vgr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long categoryId;
    @NotEmpty
    private CategoryType category;
    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    private Instant createdDate;
}