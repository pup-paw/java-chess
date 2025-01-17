package chess;

import java.util.List;

import chess.domain.ChessGame;
import chess.domain.GameTurn;
import chess.domain.board.InitialBoardGenerator;
import chess.domain.command.Command;
import chess.domain.command.CommandType;
import chess.view.InputView;
import chess.view.OutputView;

public final class ChessGameLauncher {

    void run() {
        OutputView.announceStart();
        ChessGame chessGame = new ChessGame(new InitialBoardGenerator(), GameTurn.READY);
        boolean running = true;
        while (running) {
            running = execute(chessGame);
        }
    }

    private Boolean execute(ChessGame chessGame) {
        List<String> input = InputView.getCommand();
        Command command = CommandType.getCommand(input);
        try {
            return playOneTurn(chessGame, command);
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            return execute(chessGame);
        }
    }

    private Boolean playOneTurn(ChessGame chessGame, Command command) {
        if (command.getCommandType() == CommandType.STATUS && command.canShowScore(chessGame)) {
            return command.execute(chessGame);
        }
        if (command.getCommandType() != CommandType.STATUS) {
            return command.execute(chessGame);
        }
        return true;
    }
}
