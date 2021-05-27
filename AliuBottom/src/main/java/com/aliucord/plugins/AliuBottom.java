package com.aliucord.plugins;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.Main;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.MessageEmbed;
import com.aliucord.entities.MessageEmbed.Field;
import com.aliucord.entities.Plugin;
import com.aliucord.plugins.bottom.Bottom;
import com.discord.stores.StoreStream;
import java.util.*;

@SuppressWarnings("unused")
public class AliuBottom extends Plugin {

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[]{ new Manifest.Author("ShiroUsagi", 497555706073841671L) };
        manifest.description = "AliuBottom";
        manifest.version = "1.1.0";
        manifest.updateUrl = "https://raw.githubusercontent.com/ShiroBlank/AliucordPlugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        commands.registerCommand("bottom encode", "Encode messages to bottom", Collections.singletonList(CommandsAPI.requiredMessageOption), args -> {
            String msg = (String) args.get("message");
            if (msg == null) return new CommandsAPI.CommandResult(msg);
            StringBuilder bottomEncoded = new StringBuilder(msg.trim());
            return new CommandsAPI.CommandResult(Bottom.encode(bottomEncoded.toString()));
        });

        commands.registerCommand("bottom decode", "Decodes messages from bottom", Collections.singletonList(CommandsAPI.requiredMessageOption), args -> {
            String msg = (String) args.get("message");
            if (msg == null) return new CommandsAPI.CommandResult(msg);
            String bottomDecoded = (String) msg.trim();
            MessageEmbed embed = new MessageEmbed();
            try
            {
                embed.setFields(Arrays.asList(
                        new Field("Decoded:", Bottom.decode(bottomDecoded), true)
                ));
            }
            catch(Exception e)
            {
                embed.setFields(Arrays.asList(
                        new Field("Decoded:", "Error Decoding", true)
                ));
            }
            return new CommandsAPI.CommandResult(null, Collections.singletonList(embed), false);
        });

        commands.registerCommand("bottom decodeid", "Decodes a message via ID from bottom", Collections.singletonList(CommandsAPI.requiredMessageOption), args -> {
            String msg = (String) args.get("message");
            String getIDContent = null;
            if (msg == null) return new CommandsAPI.CommandResult(msg);
            try {
                getIDContent = StoreStream.getMessages().getMessage(StoreStream.getChannelsSelected().getId(), Long.parseLong(msg)).getContent();
            } catch (Exception e) {

            }
            MessageEmbed embed = new MessageEmbed();
            try
            {
                embed.setFields(Arrays.asList(
                    new Field("Decoded:", Bottom.decode(getIDContent), true)
            ));
            }
            catch(Exception e)
            {
                embed.setFields(Arrays.asList(
                        new Field("Decoded:", "Error Decoding", true)
                ));
            }
            return new CommandsAPI.CommandResult(null, Collections.singletonList(embed), false);
        });
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }
}
