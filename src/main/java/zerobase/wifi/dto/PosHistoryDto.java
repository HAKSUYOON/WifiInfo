package zerobase.wifi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosHistoryDto {
	private int id;
	private String insertLat;
	private String insertLnt;
	private String viewDate;
}


