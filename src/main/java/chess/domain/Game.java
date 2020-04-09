package chess.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import chess.domain.board.Board;
import chess.domain.board.Position;
import chess.domain.judge.BasicJudge;
import chess.domain.judge.Judge;
import chess.domain.piece.Side;
import chess.domain.player.Player;
import chess.domain.player.Result;
import chess.exceptions.InvalidInputException;

public class Game {
    private int id;
    private final Board board;
    private final Judge judge;
    private final Map<Side, Player> players;
    private Side turn;

    public Game() {
        board = Board.init();
        judge = new BasicJudge(board);
        players = new HashMap<>();
        turn = Side.WHITE;
    }

    public Game(Player white, Player black) {
        this();
        players.put(Side.WHITE, white);
        players.put(Side.BLACK, black);
    }

    public Game(int id, Player white, Player black) {
        this(white, black);
        this.id = id;
    }

    public List<String> findAllAvailablePath(String from) {
        return board.findAllAvailablePath(Position.of(from))
            .stream()
            .map(Position::toString)
            .collect(Collectors.toList());
    }

    public boolean isWhiteTurn() {
        return turn == Side.WHITE;
    }

    public boolean isGameOver() {
        return judge.isGameOver();
    }

    public Side winnerSide() {
        return judge.winner();
    }

    public void finish() {
        Side winnerSide = judge.winner();
        if (judge.isDraw()) {
            Player white = getPlayer(Side.WHITE);
            Player black = getPlayer(Side.BLACK);
            white.finishAgainst(black, Result.DRAW);
            return;
        }
        Player winner = getPlayer(winnerSide);
        Player loser = getPlayer(winnerSide.opposite());
        winner.finishAgainst(loser, Result.WIN);
    }

    public boolean move(String from, String to) {
        try {
            board.move(Position.of(from), Position.of(to));
            turn = turn.opposite();
            return true;
        } catch (InvalidInputException e) {
            return false;
        }
    }

    public Board getBoard() {
        return board;
    }

    public Judge getJudge() {
        return judge;
    }

    public Map<Side, Player> getPlayers() {
        return players;
    }

    public Player getPlayer(Side side) {
        return players.get(side);
    }

    public int getPlayerId(Side side) {
        return players.get(side).getId();
    }

    public double getScoreOf(final Side side) {
        return judge.calculateScore(side);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Game game = (Game)o;
        return id == game.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}