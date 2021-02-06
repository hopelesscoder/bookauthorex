package com.example.bookauthor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author daniele pasquini
 *
 */
@Service
public class GenericService<Repo extends JpaRepository<ModelClass, Integer>, ModelClass extends ModelObj> {
	
	private static final Logger log = LoggerFactory.getLogger(GenericService.class);
	
	@Autowired
	private Repo repo;

	public List<ModelClass> findAll(){
		return repo.findAll();
	}

	public Optional<ModelClass> findById( int id){
		Optional<ModelClass> obj = repo.findById(id);
		return obj;
	}

	public ModelClass save(ModelClass objModel){
		objModel.setId(0);
		return repo.save(objModel);
	}

	public Optional<ModelClass> update(ModelClass objModel){
		Optional<ModelClass> obj = repo.findById(objModel.getId());
		if(!obj.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(repo.save(objModel));
	}

	public void deleteById(int id){
		repo.deleteById(id);
	}

	public void setJpaRepository(Repo repo) {
		this.repo = repo;
	}
}