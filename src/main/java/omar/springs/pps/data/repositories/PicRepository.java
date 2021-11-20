package omar.spring.pps.data.repositories;

import omar.spring.pps.data.entities.Pic;

import omar.spring.pps.data.entities.PicCategory;
import omar.spring.pps.data.entities.PicStatus;
import omar.spring.pps.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PicRepository extends JpaRepository<Pic, Long> {
  List<Pic>findAllByStatusIn(List<PicStatus>  status);
  @Query(value="update Pic m set m.status='DELETED' where m.id=?1")
  List<Pic>findAllByUser(User user);
    List<Pic>findAllByCategoryIn(List<PicCategory>categories);


}
