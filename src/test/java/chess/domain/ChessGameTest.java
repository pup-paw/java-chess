package chess.domain;

import chess.domain.piece.*;
import chess.domain.piece.info.Color;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChessGameTest {
    @DisplayName("현재 기물 확인하기")
    @Test
    void 현재_기물_확인_테스트() {
        ChessGame chessGame = new ChessGame();

        assertThat(chessGame.getPiecesByAllPosition().size()).isEqualTo(64);
    }

    @DisplayName("비숍 이동 - target에 상대 말이 있는 경우")
    @Test
    void 상대편_말을_공격한다() {
        List<Piece> current = Arrays.asList(
                new Bishop(Color.WHITE, Position.of("c8")),
                new Pawn(Color.BLACK, Position.of("f5")));
        Pieces pieces = new Pieces(current);
        ChessGame chessGame = new ChessGame(pieces);
        chessGame.movePieceFromSourceToTarget(Position.of("c8"), Position.of("f5"));

        assertThat(pieces.getPieces().size()).isEqualTo(1);
    }

    @DisplayName("비숍 이동 경로에 장애물이 있을 경우 예외")
    @Test
    void 비숍_이동에_장애물() {
        ChessGame chessGame = new ChessGame();
        Position source = Position.of("f8");
        Position target = Position.of("h6");
        assertThatThrownBy(() ->
                chessGame.movePieceFromSourceToTarget(source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("킹 - 대각선 위치인 target에 상대 말이 있는 경우")
    @Test
    void 상대편_말을_공격한다_대각선() {
        List<Piece> current = Arrays.asList(
                new King(Color.WHITE, Position.of("d8")),
                new Pawn(Color.BLACK, Position.of("e7")));
        Pieces pieces = new Pieces(current);
        ChessGame chessGame = new ChessGame(pieces);
        chessGame.movePieceFromSourceToTarget(Position.of("d8"), Position.of("e7"));

        assertThat(pieces.getPieces().size()).isEqualTo(1);
    }

    @DisplayName("킹 - 십자 위치인 target에 상대 말이 있는 경우")
    @Test
    void 상대편_말을_공격한다_십자() {
        List<Piece> current = Arrays.asList(
                new King(Color.WHITE, Position.of("d8")),
                new Pawn(Color.BLACK, Position.of("d7")));
        Pieces pieces = new Pieces(current);
        ChessGame chessGame = new ChessGame(pieces);
        chessGame.movePieceFromSourceToTarget(Position.of("d8"), Position.of("d7"));

        assertThat(pieces.getPieces().size()).isEqualTo(1);
    }

    @DisplayName("나이트 - target에 상대 말이 있는 경우")
    @Test
    void 나이트_상대편_말을_공격한다() {
        List<Piece> current = Arrays.asList(
                new Knight(Color.WHITE, Position.of("b8")),
                new Pawn(Color.BLACK, Position.of("d7")));
        Pieces pieces = new Pieces(current);
        ChessGame chessGame = new ChessGame(pieces);
        chessGame.movePieceFromSourceToTarget(Position.of("b8"), Position.of("d7"));


        assertThat(pieces.getPieces().size()).isEqualTo(1);
    }

    @DisplayName("퀸 - 십자 위치인 target에 상대 말이 있는 경우=")
    @Test
    void 퀸_상대편_말을_공격한다_십자() {
        List<Piece> current = Arrays.asList(
                new Queen(Color.WHITE, Position.of("d8")),
                new Pawn(Color.BLACK, Position.of("d1")));
        Pieces pieces = new Pieces(current);
        ChessGame chessGame = new ChessGame(pieces);
        chessGame.movePieceFromSourceToTarget(Position.of("d8"), Position.of("d1"));


        assertThat(pieces.getPieces().size()).isEqualTo(1);
    }

    @DisplayName("퀸 - 대각선 위치인 target에 상대 말이 있는 경우")
    @Test
    void 퀸_상대편_말을_공격한다_대각선() {
        List<Piece> current = Arrays.asList(
                new Queen(Color.WHITE, Position.of("d8")),
                new Pawn(Color.BLACK, Position.of("d1")));
        Pieces pieces = new Pieces(current);
        ChessGame chessGame = new ChessGame(pieces);
        chessGame.movePieceFromSourceToTarget(Position.of("d8"), Position.of("d1"));

        assertThat(pieces.getPieces().size()).isEqualTo(1);
    }

    @DisplayName("룩 - target에 상대 말이 있는 경우")
    @Test
    void 룩_상대편_말을_공격한다() {
        List<Piece> current = Arrays.asList(
                new Rook(Color.WHITE, Position.of("a8")),
                new Pawn(Color.BLACK, Position.of("a5")));
        Pieces pieces = new Pieces(current);
        ChessGame chessGame = new ChessGame(pieces);
        chessGame.movePieceFromSourceToTarget(Position.of("a8"), Position.of("a5"));

        assertThat(pieces.getPieces().size()).isEqualTo(1);
    }

    @DisplayName("폰 - 이동 경로에 장애물이 있을 경우")
    @Test
    void 이동하는데_앞에_장애물이_있는_경우() {
        List<Piece> current = Arrays.asList(
                new Pawn(Color.BLACK, Position.of("a7")),
                new Pawn(Color.BLACK, Position.of("a6")));
        Pieces pieces = new Pieces(current);
        Position source = Position.of("a7");
        Position target = Position.of("a5");

        ChessGame chessGame = new ChessGame(pieces);

        assertThatThrownBy(() ->
                chessGame.movePieceFromSourceToTarget(source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
