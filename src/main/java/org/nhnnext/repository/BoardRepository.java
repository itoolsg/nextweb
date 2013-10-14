package org.nhnnext.repository;

import java.io.Serializable;

import org.nhnnext.web.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {// Serizable = ID
	

}
