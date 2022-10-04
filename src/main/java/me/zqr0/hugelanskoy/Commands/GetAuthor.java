package me.zqr0.hugelanskoy.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetAuthor implements CommandExecutor {

    private String ghLink = "https://github.com/ZQR0";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        player.sendMessage(ChatColor.GREEN + "ZQR0\nJava Developer\nGitHub --> " + this.ghLink);
        return true;
    }
}
