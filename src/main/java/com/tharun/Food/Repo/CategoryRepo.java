package com.tharun.Food.Repo;

import com.tharun.Food.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;
import java.util.List;
@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {



    public List<Category>findByRestaurantId(Long id);
}
