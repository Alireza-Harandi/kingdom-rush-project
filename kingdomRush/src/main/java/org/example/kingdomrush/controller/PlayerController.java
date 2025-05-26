package org.example.kingdomrush.controller;

import org.example.kingdomrush.model.DataBase;
import org.example.kingdomrush.model.Player;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlayerController {
    private static PlayerController playerController;

    private PlayerController(){}

    public static PlayerController getPlayerController(){
        if(playerController==null)
            playerController = new PlayerController();
        return playerController;
    }

    private Player player;

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    private int maxId() throws SQLException {
        String sqlCmd = String.format("SELECT MAX(id) FROM players");
        ResultSet resultSet = DataBase.getDataBase().executeQuery(sqlCmd);
        if(resultSet.next())
            return resultSet.getInt(1);
        else
            return 0;
    }

    public boolean signup(String username, String password) throws SQLException {
        String sqlCmd = String.format("SELECT username FROM players WHERE username='%s'", username);
        ResultSet resultSet = DataBase.getDataBase().executeQuery(sqlCmd);
        if(resultSet != null && resultSet.next())
            return false;
        int id = maxId()+1;
        sqlCmd = String.format("INSERT INTO players (id,username,password) VALUES (%s,'%s','%s')", id, username, password);
        DataBase.getDataBase().executeSQL(sqlCmd);
        PlayerController.getPlayerController().setPlayer(new Player(id,username,password,1,2500,"0,0,0,0"));
        return true;
    }

    public boolean login(String username, String password) throws SQLException {
        String sqlCmd = String.format("SELECT id,username,password,level,gem,bag FROM players WHERE username='%s' AND password='%s'", username, password);
        ResultSet resultSet = DataBase.getDataBase().executeQuery(sqlCmd);
        if(resultSet.next()){
            PlayerController.getPlayerController().setPlayer(new Player(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6)
            ));
            return true;
        }
        else
            return false;
    }

    public boolean buySpell(List<Integer> spells) throws SQLException {
        //health,freeze,coin,littleBoy
        int price = spells.getFirst()*350 + spells.get(1)*250 + spells.get(2)*850 + spells.getLast()*999;
        if(price > player.getGem())
            return false;
        String sqlCmd = String.format("SELECT bag FROM players WHERE id=%s", player.getId());
        ResultSet resultSet = DataBase.getDataBase().executeQuery(sqlCmd);
        String[] spell = new String[4];
        while (resultSet.next())
            spell = resultSet.getString(1).split(",");
        spell[0] = String.valueOf(spells.getFirst()+Integer.parseInt(spell[0]));
        spell[1] = String.valueOf(spells.get(1)+Integer.parseInt(spell[1]));
        spell[2] = String.valueOf(spells.get(2)+Integer.parseInt(spell[2]));
        spell[3] = String.valueOf(spells.get(3)+Integer.parseInt(spell[3]));
        String result = Integer.valueOf(spell[0])+","+Integer.valueOf(spell[1])+","+Integer.valueOf(spell[2])+","+Integer.valueOf(spell[3]);

        player.setGem(player.getGem()-price);
        player.setBag(result);

        sqlCmd = String.format("UPDATE players SET bag='%s' , gem=%s WHERE id=%s", result, player.getGem(), player.getId());
        DataBase.getDataBase().executeSQL(sqlCmd);
        return true;
    }

    public boolean editeProfile(String username, String password) throws SQLException {
        if(!player.getUsername().equals(username)) {
            String sqlCmd = String.format("SELECT username FROM players WHERE username='%s'", username);
            ResultSet resultSet = DataBase.getDataBase().executeQuery(sqlCmd);
            if (resultSet.next())
                return false;
        }
        if(player!=null) {
            String sqlCmd = String.format("UPDATE players SET username='%s', password='%s' WHERE id=%s", username, password, player.getId());
            DataBase.getDataBase().executeSQL(sqlCmd);
        }
            PlayerController.getPlayerController().getPlayer().setUsername(username);
            PlayerController.getPlayerController().getPlayer().setPassword(password);
        return true;
    }

    public void editDetail(){
        String sqlCmd = String.format("UPDATE players SET level=%s , gem=%s , bag='%s' WHERE id=%s", player.getLevel(), player.getGem(), player.getBag(), player.getId());
        DataBase.getDataBase().executeSQL(sqlCmd);
    }

}
