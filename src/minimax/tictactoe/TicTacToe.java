package minimax.tictactoe;

import java.util.*;

public class TicTacToe {
    int[] board = new int[9];
    public int player = 1;

    public TicTacToe() { }
    public TicTacToe(int[] board, int player) {
        this.board = board; this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TicTacToe)) return false;

        TicTacToe t = (TicTacToe) o;
        return player == t.player && Arrays.equals(board, t.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board) + player;
    }

    @Override
    public TicTacToe clone() {
        return new TicTacToe(board.clone(), player);
    }

    List<Integer> actions() {
        List<Integer> r = new ArrayList<Integer>();
        if (!isDone())
            for (int i = 0 ; i < 9 ; ++i)
                if (board[i] == 0)
                    r.add(i);
        return r;
    }

    int countEmpty() {
        int count = 0;
        for (int i = 0 ; i < 9 ; ++i)
            if (board[i] == 0)
                ++count;

        return count;
    }

    int randomAction(Random random) {
        int m = random.nextInt(countEmpty());
        int i = -1;
        for (int j = 0 ; j <= m ; ++j)
            do {
                i += 1;
            } while (board[i] != 0);

        return i;
    }

    void apply(int action) {
        if (board[action] != 0 || isDone())
            throw new Error("illegal move");

        board[action] = player;
        player = 3 - player;
    }

    TicTacToe result(int action) {
        TicTacToe s = clone();
        s.apply(action);
        return s;
    }

    int winner(int s, int d) {
        int w = board[s];

        return board[s + d] == w && board[s + 2 * d] == w ? w : 0;
    }

    static int[] check =   { 0, 1, 2, 0, 3, 6, 0, 2 };
    static int[] check_d = { 3, 3, 3, 1, 1, 1, 4, 2 };

    public int winner() {
        for (int i = 0 ; i < check.length ; ++i) {
            int w = winner(check[i], check_d[i]);
            if (w > 0)
                return w;
        }
        return 0;
    }

    public boolean isDone() {
        return (winner() != 0 || countEmpty() == 0);
    }

    char asChar(int i) {
        switch (i) {
        case 0: return '.';
        case 1: return 'X';
        case 2: return 'O';
        default: throw new Error();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0 ; i < 3 ; ++i) {
            for (int j = 0 ; j < 3 ; ++j)
                sb.append(String.format("%c ", asChar(board[3 * i + j])));
            sb.append("\n");
        }

        return sb.toString();
    }
}