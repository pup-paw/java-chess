package chess.domain;

import java.util.List;
import java.util.Map;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.File;
import chess.domain.position.Square;

public class Status {
    private final Board board;

    public Status(Board board) {
        this.board = board;
    }

    public double calculateScore(Color color) {
        List<Map.Entry<Square, Piece>> survives = board.filterBy(color);
        return adjustmentSum(getSum(survives), survives);
    }

    private double getSum(List<Map.Entry<Square, Piece>> survives) {
        double sum = 0;
        for (Map.Entry<Square, Piece> survive : survives) {
            Piece piece = survive.getValue();
            sum = piece.addScore(sum);
        }
        return sum;
    }

    private double adjustmentSum(double sum, List<Map.Entry<Square, Piece>> survives) {
        for (File file : File.values()) {
            int count = countPawnInSameFile(survives, file);
            sum = subtractPawnInSameFile(sum, count);
        }
        return sum;
    }

    private int countPawnInSameFile(List<Map.Entry<Square, Piece>> survives, File file) {
        return (int)survives.stream()
                .filter(entry -> entry.getValue().isPawn())
                .filter(entry -> entry.getKey().checkFile(file))
                .count();
    }

    private double subtractPawnInSameFile(double sum, int count) {
        if (count > 1) {
            sum -= 0.5 * count;
        }
        return sum;
    }
}