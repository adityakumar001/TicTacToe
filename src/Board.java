import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class Board extends JPanel {

    Board() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(new PlayBoard(Game.getInstance().getMatrixSize()));
        add(new MiscBoard());
    }

    private class PlayBoard extends JPanel implements MouseListener {
        private int matrixSize;

        PlayBoard(int matrixSize) {
            this.matrixSize = matrixSize;
            System.out.println(this.matrixSize);

            setPreferredSize
                    (new Dimension
                            (this.matrixSize * Cell.CELL_SIZE, this.matrixSize * Cell.CELL_SIZE));

            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            addMouseListener(this);

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            for (int i = 100; i < Cell.CELL_SIZE * matrixSize; i += 100) {
                g2d.drawLine(i, 0, i, Cell.CELL_SIZE * matrixSize);
                g2d.drawLine(0, i, Cell.CELL_SIZE * matrixSize, i);
            }
            for (Cell cell : Game.getInstance().getCells()) {
                g2d.drawString("" +
                                (cell.getSymbol() == Cell.EMPTY ? "" :
                                        cell.getSymbol()),
                        cell.getColumn() * Cell.CELL_SIZE + Cell.CELL_SIZE / 2,
                        cell.getRow() * Cell.CELL_SIZE + Cell.CELL_SIZE / 2);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = e.getY() / Cell.CELL_SIZE;
            int column = e.getX() / Cell.CELL_SIZE;
            Game.getInstance().markCell(row, column);
            Board.this.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class MiscBoard extends JPanel {
        JLabel player1Wins, player2Wins, draws, totalMatches;
        Box scoreBox, optionsBox, matrixBox;
        private JButton clearButton;
        private JComboBox<String> matrixSizeComboBox;
        private JLabel matrixSizeLabel;

        MiscBoard() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            initialize();
            addComponents();
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        private void initialize() {

            scoreBox = Box.createVerticalBox();
            totalMatches = new JLabel("Total Matches : " + Game.getInstance().getTotalMatches());
            player1Wins = new JLabel("Player 1  " + " ( " + Game.getInstance().getPlayer1().getSymbol() + " ) : " +
                    +Game.getInstance().getScores()[Game.PLAYER_1_WINS]);
            player2Wins = new JLabel("Player 2  " + " ( " + Game.getInstance().getPlayer2().getSymbol() + " ) : " +
                    +Game.getInstance().getScores()[Game.PLAYER_2_WINS]);
            draws = new JLabel("Drawn : " + Game.getInstance().getScores()[Game.DRAWS_INDEX]);

            optionsBox = Box.createVerticalBox();

            clearButton = new JButton("Clear".toUpperCase());
            clearButton.addActionListener(e -> {
                Game.getInstance().startGame();
                Board.this.repaint();

            });

            matrixBox = Box.createHorizontalBox();
            matrixBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            matrixSizeLabel = new JLabel("Matrix : ");
            matrixSizeComboBox = new JComboBox<>(new String[]{"3 x 3", "4 x 4", "5 x 5"});
            switch (Game.getInstance().getMatrixSize()) {
                case 3:
                    matrixSizeComboBox.setSelectedIndex(0);
                    break;
                case 4:
                    matrixSizeComboBox.setSelectedIndex(1);
                    break;
                case 5:
                    matrixSizeComboBox.setSelectedIndex(2);
                    break;
            }
            matrixSizeComboBox.addActionListener(e -> {
                switch (matrixSizeComboBox.getItemAt(matrixSizeComboBox.getSelectedIndex())) {
                    case "3 x 3":
                        Game.getInstance().setMatrixSize(3);
                        repaint();
                        break;
                    case "4 x 4":
                        Game.getInstance().setMatrixSize(4);

                        break;
                    case "5 x 5":
                        Game.getInstance().setMatrixSize(5);
                        repaint();
                        break;
                }
                Board.this.repaint();
                Game.getInstance().startGame();
            });

        }

        private void addComponents() {

            matrixBox.add(matrixSizeLabel);
            matrixBox.add(matrixSizeComboBox);

            optionsBox.add(matrixBox);
            optionsBox.add(Box.createVerticalGlue());
            optionsBox.add(clearButton);
            optionsBox.add(Box.createVerticalGlue());

            scoreBox.add(totalMatches);
            scoreBox.add(Box.createVerticalGlue());
            scoreBox.add(player1Wins);
            scoreBox.add(Box.createVerticalGlue());
            scoreBox.add(player2Wins);
            scoreBox.add(Box.createVerticalGlue());
            scoreBox.add(draws);

            add(optionsBox);
            add(Box.createHorizontalGlue());
            add(scoreBox);

        }

    }

}

