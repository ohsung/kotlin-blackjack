package blackjack.view

import blackjack.domain.BlackJackGame
import blackjack.domain.Card
import blackjack.domain.Participant

object GameView {
    fun giveCard(participants: BlackJackGame) {
        participants.players.joinToString { it.name }.also {
            println("${it}에게 2장을 나눠주었습니다.")
        }
    }

    fun displayInitialCard(participants: BlackJackGame) {
        participants.players.forEach {
            println("${it.name}카드 : ${getCardDisplayName(it)}")
        }
    }

    fun displayPlayerCard(participant: Participant) {
        participant.also {
            println("${it.name}카드 : ${getCardDisplayName(it)}")
        }
    }

    fun displayResult(participants: BlackJackGame) {
        participants.players.forEach {
            print("${it.name}카드 : ${getCardDisplayName(it)}")
            print(" - ")
            println("결과 : ${it.cards.score()}")
        }
    }

    private fun getCardDisplayName(player: Participant): String {
        return player.cards.playerCards.joinToString { "${it.denomination.displayName}${it.pattern.toDisplayName()}" }
    }

    private fun Card.CardPattern.toDisplayName(): String {
        return when (this) {
            Card.CardPattern.CLUBS -> "클로버"
            Card.CardPattern.SPADES -> "스페이드"
            Card.CardPattern.HEARTS -> "하트"
            Card.CardPattern.DIAMONDS -> "다이아몬드"
        }
    }
}