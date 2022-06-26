package blackjack.model.player

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("플레이어 테스트")
class PlayerTest {

    @Test
    fun `플레이어 생성시 가지고 있는 카드 개수는 0개`() {
        // given, when, then
        assertThat(Player.from("aiden").cardSize).isEqualTo(0)
    }
}