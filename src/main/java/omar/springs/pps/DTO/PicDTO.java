package omar.spring.pps.DTO;

import lombok.Getter;
import lombok.Setter;
import omar.spring.pps.data.entities.PicCategory;
import omar.spring.pps.data.entities.User;

@Getter
@Setter
public class PicDTO {
    private User user ;
    private  String description;
    private PicCategory picCategory ;
}
