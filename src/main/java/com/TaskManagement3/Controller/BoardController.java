package com.TaskManagement3.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement3.Entity.Board;
import com.TaskManagement3.Entity.BoardColumn;
import com.TaskManagement3.Service.BoardService;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    
    @Autowired
    private BoardService boardService;
    
    
    @PostMapping("/createBoard")
    public ResponseEntity<Board> createBoard(@RequestBody Board board){
        return ResponseEntity.ok(boardService.createBoard(board));
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Board>> getBoard(@PathVariable Long id){
        return ResponseEntity.ok(boardService.findByBoardId(id));
    }
    
    @GetMapping("/column/{id}")
    public ResponseEntity<List<BoardColumn>> getColumns(@PathVariable Long id){
        return ResponseEntity.ok(boardService.getBoardColumn(id));
    }
    
    @PostMapping("/{id}/column")
    public ResponseEntity<Board> addColumn(@PathVariable Long id,@RequestBody BoardColumn column){
        
        column.setBoard(boardService.findByBoardId(id).orElseThrow(null));
        return ResponseEntity.ok(boardService.createBoard(column.getBoard()));
    }
    
    @PostMapping("/{id}/cards/{cardId}/move")
    public ResponseEntity<String>moveCard(@PathVariable Long id,
                                          @PathVariable Long cardId,
                                          @RequestBody Map<String,Object> body,
                                          @RequestHeader(value="X_user_email",required=false) String user){
        
        Long columnId=Long.valueOf(String.valueOf(body.get("columnId")));
        int postion= Integer.parseInt(String.valueOf(body.getOrDefault("postion", "0")));
        boardService.moveCards(cardId, cardId, columnId, postion, user == null? "system":user);
        
        return ResponseEntity.ok("Moved");
    }
    
    
    
}