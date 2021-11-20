package omar.spring.pps.service;

import omar.spring.pps.data.entities.Pic;
import omar.spring.pps.data.entities.PicCategory;
import omar.spring.pps.data.entities.PicStatus;
import omar.spring.pps.data.entities.User;
import omar.spring.pps.data.repositories.PicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PicServiceImpl implements PicService {
    private final PicRepository repository;
    @Autowired
    public PicServiceImpl(PicRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<Pic> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public List<Pic> getAllByStatus(List<PicStatus> status) {
        return new ArrayList<>(repository.findAllByStatusIn(status));
    }

    @Override
    public List<Pic> getAllByCategory(List<PicCategory> categories) {
        return new ArrayList<>(repository.findAllByCategoryIn(categories));
    }

    @Override
    public List<Pic> getAllByUser(User user) {
        return new ArrayList<>(repository.findAllByUser(user));
    }

    @Override
    public Optional<Pic> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Pic save(Pic pic) {
        return repository.save(pic);
    }

    @Override
    public void delete(Pic pic) {
repository.delete(pic);
    }
}
