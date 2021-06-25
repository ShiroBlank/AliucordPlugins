package com.aliucord.plugins;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.Main;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.entities.Plugin;
import com.aliucord.plugins.bottom.Bottom;
import com.aliucord.wrappers.messages.MessageWrapper;
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
        manifest.version = "1.1.4";
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
            MessageEmbedBuilder embed = new MessageEmbedBuilder();
            try
            {
                embed.addField("Decoded:", Bottom.decode(bottomDecoded), true);
            }
            catch(Exception e)
            {
                embed.addField("Decoded:", "Error Decoding", true);
            }
            return new CommandsAPI.CommandResult(null, Collections.singletonList(embed.build()), false);
        });

        commands.registerCommand("bottom decodeid", "Decodes a message via ID from bottom", Collections.singletonList(CommandsAPI.requiredMessageOption), args -> {
            String msg = (String) args.get("message");
            String getIDContent = null;
            MessageEmbedBuilder embed = new MessageEmbedBuilder();
            if (msg == null) return new CommandsAPI.CommandResult(msg);
            try {
                getIDContent = MessageWrapper.getContent(StoreStream.getMessages().getMessage(StoreStream.getChannelsSelected().getId(), Long.parseLong(msg)));
            } catch (Exception e) {
                embed.addField("Decoded:", "Error Decoding", true);
            }

            try
            {
                embed.addField("Decoded:", Bottom.decode(getIDContent), true);
            }
            catch(Exception e)
            {
                embed.addField("Decoded:", "Error Decoding", true);
            }
            return new CommandsAPI.CommandResult(null, Collections.singletonList(embed.build()), false);
        });
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }
}
