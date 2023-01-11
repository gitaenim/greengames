package com.green.nowon.domain.entity.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.nowon.domain.entity.board.BoardEntity;

@Repository
public interface BoardEntityRepository extends JpaRepository<BoardEntity, Long>{

	List<BoardEntity> findAllByOrderByBnoDesc();

	Page<BoardEntity> findAllByOrderByBnoDesc(Pageable pageable);
}
