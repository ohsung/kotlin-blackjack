package blackjack

import baclkjack.domain.card.Card
import baclkjack.domain.card.Deck
import baclkjack.domain.card.Number
import baclkjack.domain.card.Suit
import baclkjack.domain.play.Dealer
import baclkjack.domain.play.GameResult
import baclkjack.domain.play.Money
import baclkjack.domain.play.Player
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class PlayerTest : StringSpec({

    "a Player 를 생성한다" {
        val player = Player("a")
        player.name shouldBe "a"
    }

    "내 카드가 22 이상이 경우  burst" {
        val player = Player("a")
        val deck = Deck(
            cards = mutableListOf<Card>().apply {
                add(Card(Suit.HEART, Number.KING))
                add(Card(Suit.HEART, Number.QUEEN))
                add(Card(Suit.HEART, Number.JACK))
            }
        )
        player.start(deck)
        player.hit(deck)
        player.burst().shouldBeTrue()
        player.score() shouldBe 30
    }

    "딜러가 17 내 카드가 21 인경우 blackjack 수익은 1500" {
        val dealer = Dealer()
        val player = Player("a", Money(1_000))
        val deck = Deck(
            cards = mutableListOf(
                Card(Suit.HEART, Number.KING),
                Card(Suit.HEART, Number.SEVEN),
                Card(Suit.HEART, Number.ACE),
                Card(Suit.HEART, Number.KING)
            )
        )
        dealer.start(deck)
        player.start(deck)
        player.blackJack().shouldBeTrue()
        player.score() shouldBe 21

        player.result(dealer) shouldBe 1500
        player.ofGameState(dealer) shouldBe GameResult.BLACKJACK
    }

    "딜러가 17 내카드가 18 인 result WIN 수익은 1000" {
        val dealer = Dealer()
        val player = Player("a", Money(1_000))
        val deck = Deck(
            cards = mutableListOf<Card>().apply {
                add(Card(Suit.HEART, Number.KING))
                add(Card(Suit.HEART, Number.SEVEN))
                add(Card(Suit.HEART, Number.QUEEN))
                add(Card(Suit.HEART, Number.EIGHT))
            }
        )
        dealer.start(deck)
        player.start(deck)

        player.result(dealer) shouldBe 1000
        player.ofGameState(dealer) shouldBe GameResult.WIN
    }

    "딜러가 18 내카드가 18 인 result DRAW 수익은 0" {
        val dealer = Dealer()
        val player = Player("a", Money(1_000))
        val deck = Deck(
            cards = mutableListOf<Card>().apply {
                add(Card(Suit.HEART, Number.KING))
                add(Card(Suit.HEART, Number.EIGHT))
                add(Card(Suit.HEART, Number.QUEEN))
                add(Card(Suit.HEART, Number.EIGHT))
            }
        )
        dealer.start(deck)
        player.start(deck)

        player.result(dealer) shouldBe 0
        player.ofGameState(dealer) shouldBe GameResult.DRAW
    }

    "딜러가 21 내카드가 20 인 result LOST, 수익은 -1000" {
        val dealer = Dealer()
        val player = Player("a", Money(1_000))
        val deck = Deck(
            cards = mutableListOf<Card>().apply {
                add(Card(Suit.HEART, Number.ACE))
                add(Card(Suit.HEART, Number.QUEEN))
                add(Card(Suit.HEART, Number.KING))
                add(Card(Suit.HEART, Number.EIGHT))
                add(Card(Suit.HEART, Number.TWO))
            }
        )
        dealer.start(deck)
        player.start(deck)
        player.hit(deck)
        player.result(dealer) shouldBe -1000
        player.ofGameState(dealer) shouldBe GameResult.LOSE
    }
})
