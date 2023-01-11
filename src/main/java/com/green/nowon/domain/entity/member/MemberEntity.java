package com.green.nowon.domain.entity.member;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.green.nowon.domain.dto.memberDTO.MemberUpdateDTO;
import com.green.nowon.domain.entity.BaseDateEntity;
import com.green.nowon.security.MyRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author LeeYongJu
 * 직원 관련 DB
 * DB 이름 : GgMember
 * 컬럼 목록 : mno(사번) ,name(이름), id(아이디) , pass(비밀번호) , phone(번호)  
 * , mem_img(프로필)
 * myRole : user(사원) , admin(팀장)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "seq_gen_GgMember", 
		sequenceName = "seq_GgMember", initialValue = 1, allocationSize = 1)
@Table(name = "GgMember")
@Entity
public class MemberEntity extends BaseDateEntity{
	
	@GeneratedValue(generator = "seq_gen_GgMember", strategy = GenerationType.SEQUENCE)
	@Id
	private long mno;//사원번호
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String id;//이메일x ->수정 id로 변경 (domain은 모두 같은 부분을 사용하기 때문에)
	
	@Column(nullable = false)
	private String pass;//비밀번호
	
	@Column(nullable = false)
	private String phone;//번호
	
	@Builder.Default
	@OneToMany(mappedBy = "member")
	List<ProfileEntity> profile=new ArrayList<>();
	
	@Builder.Default
	@CollectionTable(name = "GgDeploy")
	@Enumerated(EnumType.STRING)//직책
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<MyRole> roles = new HashSet<>();
	
	public MemberEntity addRole(MyRole role) {
		roles.add(role);
		return this;
	}
	
	public MemberEntity update(MemberUpdateDTO dto) {
		this.phone = dto.getPhone();
		return this;
	}
	/**
	 * 대표이미지 없는데 없으면 @builder가 안먹힘
	 * @return
	 */
	public ProfileEntity defImg() {
		for(ProfileEntity pimg:profile) {
			return pimg;
		}
		return profile.get(0);
	}
	
	
	
}