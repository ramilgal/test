package ru.skypro.lessons.springboot.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skypro.lessons.springboot.test.model.Position;
@Data
@AllArgsConstructor
public class PositionDTO {
    private Integer id;
    private String position;
//    private String position;
    public PositionDTO() {
    }

    // Метод для преобразования сущности Position в объект PositionDTO
    public static PositionDTO fromPosition(Position position) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setId(position.getId());
        positionDTO.setPosition(position.getPosition());
        return positionDTO;
    }

    // Метод для преобразования объекта PositionDTO в сущность Position
    public Position toPosition() {
        Position position = new Position();
        position.setId(this.getId());
        position.setPosition(this.getPosition());
        return position;
    }
}
