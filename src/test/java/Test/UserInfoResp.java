package Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The user information class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResp implements Serializable {

    /**
     * The user's {@code mid}.
     */
    private long mid;

    /**
     * The number of user's coins that he/she currently owns.
     */
    private int coin;

    /**
     * The user's following {@code mid}s.
     */
    private long[] following;

}
