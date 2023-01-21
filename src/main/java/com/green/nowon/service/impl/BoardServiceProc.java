package com.green.nowon.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.green.nowon.domain.dto.board.BoardDetailDTO;
import com.green.nowon.domain.dto.board.BoardListDTO;
import com.green.nowon.domain.dto.board.BoardSaveDTO;
import com.green.nowon.domain.dto.board.BoardUpdateDTO;
import com.green.nowon.domain.dto.board.GenBoardDetailDTO;
import com.green.nowon.domain.dto.board.GenBoardListDTO;
import com.green.nowon.domain.dto.board.GenBoardSaveDTO;
import com.green.nowon.domain.dto.board.GenBoardUpdateDTO;
import com.green.nowon.domain.entity.board.BoardEntityRepository;
import com.green.nowon.domain.entity.board.BoardImgEntityRepository;
import com.green.nowon.domain.entity.board.GenBoardEntityRepository;
import com.green.nowon.domain.entity.board.GeneralBoardEntity;
import com.green.nowon.domain.entity.board.BoardEntity;
import com.green.nowon.domain.entity.member.MemberEntity;
import com.green.nowon.service.BoardService;
import com.green.nowon.util.MybFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceProc implements BoardService{

	@Value("${file.location.noticetemp}")
	private String locationTemp;
	
	@Value("${file.location.noticeupload}")
	private String locationUpload;
	
	@Autowired
	private final BoardEntityRepository repository;
	@Autowired
	private final GenBoardEntityRepository repo;
	@Autowired
	private final BoardImgEntityRepository imgRepo; //이미지
	
	
	@Transactional
	@Override
	public void getListAll(int page, Model model) {
		//board list를 페이지로 전송
		
		//페이징 작업 pageable이용
		int size=5;
		Sort sort=Sort.by(Direction.DESC, "bno");
		
		Pageable pageable=PageRequest.of(page-1, size ,sort);
		Page<BoardEntity> result=repository.findAll(pageable);
		
		
		
		int nowPage = result.getNumber()+1;
		int startPage = Math.max(nowPage -3, 1);
		int endPage = Math.min(nowPage +3, result.getTotalPages());
		int totPage= result.getTotalPages();
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("totPage", totPage);
		
		model.addAttribute("p", result);
		model.addAttribute("list", result.stream()
				.map(BoardListDTO::new)
				.collect(Collectors.toList()));
	}

    //검색, 페이징
    @Transactional
	@Override
	public void search(String keyword,  Model model, int page) {
    	
		/*
		 * List<BoardListDTO> searchList =
		 * repository.findByTitleContaining(keyword).stream().map(BoardListDTO::new)
		 * .collect(Collectors.toList());
		 */ //검색만
    	 
     	int size=5;
 		Sort sort=Sort.by(Direction.DESC, "bno");
 		Pageable pageable=PageRequest.of(page-1, size ,sort);
 		
 		Page<BoardEntity> result=repository.findByTitleContaining(keyword, pageable); //검색, 페이징 동시에
 		
 		int nowPage = result.getNumber()+1;
 		int startPage = Math.max(nowPage -3, 1);
 		int endPage = Math.min(nowPage +3, result.getTotalPages());
 		int totPage= result.getTotalPages();
 		
 		model.addAttribute("nowPage", nowPage);
 		model.addAttribute("startPage", startPage);
 		model.addAttribute("endPage", endPage);
 		model.addAttribute("totPage", totPage);
 		
 		model.addAttribute("keyword", keyword);
    	model.addAttribute("p", result);
    	model.addAttribute("searchList", result.stream().map(BoardListDTO::new).collect(Collectors.toList())); //검색, 페이징 동시에
		
    	//System.err.println(">>>>>>>>>>>>"+keyword);
	}
	
	@Transactional
	@Override
	public void sendDetail(long bno, Model model) {
		model.addAttribute("detail", repository.findById(bno)
				.map(BoardDetailDTO::new)
				.orElseThrow());
		
	}
	

	@Override
	public void save(BoardSaveDTO dto, String name) {
	}
	
	@Transactional
	@Override
	public void save(BoardSaveDTO dto) {
		
		BoardEntity entity=BoardEntity.builder()
				.title(dto.getTitle()).content(dto.getContent())
				.member(MemberEntity.builder().mno(dto.getMno()).build())
				.build();
		repository.save(entity);
		
		dto.toBoardListImgs(entity, locationUpload).forEach(imgRepo::save); //이미지
		
	}
	
	@Override //이미지
	public Map<String, String> fileTempUpload(MultipartFile bimg) {
		
		return MybFileUtils.fileUpload(bimg, locationTemp);
	}

	@Override
	public void delete(long bno) {
		repository.deleteById(bno);
	}

	@Override
	public void update(long bno, BoardUpdateDTO dto) {
		Optional<BoardEntity> result= repository.findById(bno);
		
		//존재하면 수정
		if(result.isPresent()) {
			BoardEntity entity=result.get();
			entity.update(dto);
			//업데이트 반영
			repository.save(entity);//이미 존재하는 Pk이면 수정됨
		}
		
	}
	
	//공지사항 조회수
    @Transactional
    public int updateReadCount(Long bno) {
        return repository.updateReadCount(bno);
    }
    

	
	/* 여기서부터 자유게시판 입니다 */
	
	@Transactional
	@Override
	public void getListAll02(int page, Model model) {
		//board list를 페이지로 전송
		int size=10;
		Sort sort=Sort.by(Direction.DESC, "bno");
		
		Pageable pageable=PageRequest.of(page-1, size, sort);
		Page<GeneralBoardEntity> result=repo.findAll(pageable);
		int nowPage = result.getNumber()+1;
		int startPage = Math.max(nowPage-4, 1);
		int endPage = Math.min(nowPage+5, result.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		model.addAttribute("p2", result);
		model.addAttribute("list2", result.stream()
				.map(GenBoardListDTO::new)
				.collect(Collectors.toList()));
	}

	@Override
	public void sendDetail02(long bno, Model model) {
		model.addAttribute("detail2", repo.findById(bno)
				.map(GenBoardDetailDTO::new)
				.orElseThrow());
		
	}

	@Override
	public void save02(GenBoardSaveDTO dto, String name) {
	}
	
	@Override
	public void save02(GenBoardSaveDTO dto) {
		
		GeneralBoardEntity entity=GeneralBoardEntity.builder()
				.title(dto.getTitle()).content(dto.getContent())
				.member(MemberEntity.builder().mno(dto.getMno()).build())
				.build();
		repo.save(entity);
		
	}

	@Override
	public void delete02(long bno) {
		repo.deleteById(bno);
	}

	@Override
	public void update02(long bno, GenBoardUpdateDTO dto) {
		Optional<GeneralBoardEntity> result= repo.findById(bno);
		
		//존재하면 수정
		if(result.isPresent()) {
			GeneralBoardEntity entity=result.get();
			entity.update(dto);
			//업데이트 반영
			repo.save(entity);//이미 존재하는 Pk이면 수정됨
		}
		
	}
	
	//자유조회수
    @Transactional
    public int genUpdateReadCount(Long bno) {
        return repo.genUpdateReadCount(bno);
    }

	
	

    
    
	//mypage에 띄우는 리스트
    
	@Transactional
	@Override
	public void myGetListAll(Model model) {
		
		List<BoardListDTO> result=repository.findAll().stream()
				.map(BoardListDTO::new).collect(Collectors.toList());
		
		model.addAttribute("list", result);
		
	}

	@Transactional
	@Override
	public void myGetListAll02(Model model) {
		List<GenBoardListDTO> result=repo.findAll().stream()
				.map(GenBoardListDTO::new).collect(Collectors.toList());
		
		model.addAttribute("list2", result);
		
	}

	//자유 검색
	@Transactional
	@Override
	public void search02(String keyword, Model model) {
		List<GenBoardListDTO> searchResult= repo.findByTitleContaining(keyword)
				.stream().map(GenBoardListDTO::new).collect(Collectors.toList());
		model.addAttribute("searchResult", searchResult);
	}



}
