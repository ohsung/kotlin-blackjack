package baclkjack.domain

import baclkjack.domain.card.Deck
import baclkjack.domain.play.*

class BlackJackGame(playersName: List<String>, betting: List<Int>, private val deck: Deck = Deck.createDeck()) {

    val players = playersName.mapIndexed { index, s ->
        Player(name = s, money = Money(betting[index]))
    }
    val dealer = Dealer()

    fun start() {
        dealer.start(deck)
        players.forEach {
            it.start(deck)
        }
    }

    fun play(cardDrawListener: CardDrawListener, out: (User) -> Unit) {
        players.forEach {
            it.cardDrawListener = cardDrawListener
            playGame(it, out)
        }
    }

    fun dealerPlay(out: (User) -> Unit) {
        playGame(dealer, out)
    }

    private fun playGame(user: User, out: (User) -> Unit) {
        while (user.isDraw()) {
            user.hit(deck)
            out(user)
            if (user.burst() || user.winNumber()) {
                break
            }
        }
    }
}
