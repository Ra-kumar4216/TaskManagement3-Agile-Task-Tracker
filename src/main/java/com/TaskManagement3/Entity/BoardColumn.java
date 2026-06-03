package com.TaskManagement3.Entity;


import com.TaskManagement3.Enum.IssueStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="board_columns",indexes= {@Index(columnList="board_id,postion")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardColumn {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private IssueStatus statusKey;
    
    private Integer position;
    private Integer wipLimit;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IssueStatus getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(IssueStatus statusKey) {
		this.statusKey = statusKey;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getWipLimit() {
		return wipLimit;
	}

	public void setWipLimit(Integer wipLimit) {
		this.wipLimit = wipLimit;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
    
    
    }
