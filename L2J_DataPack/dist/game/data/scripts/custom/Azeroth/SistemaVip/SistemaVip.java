/*
 * Copyright (C) 2004-2015 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package custom.Azeroth.SistemaVip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import ai.npc.AbstractNpcAI;

import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.handler.AdminCommandHandler;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.events.EventType;
import com.l2jserver.gameserver.model.events.ListenerRegisterType;
import com.l2jserver.gameserver.model.events.annotations.RegisterEvent;
import com.l2jserver.gameserver.model.events.annotations.RegisterType;
import com.l2jserver.gameserver.model.events.impl.character.player.OnPlayerLogin;

/**
 * @author Eze
 */
public class SistemaVip extends AbstractNpcAI implements IAdminCommandHandler
{
	
	private final String[] VCOMMANDS =
	{
		"admin_set_vip",
		"admin_remove_vip"
	};
	
	private SistemaVip()
	{
		super(SistemaVip.class.getSimpleName(), "custom");
		load();
		AdminCommandHandler.getInstance().registerHandler(this);
	}
	
	@RegisterEvent(EventType.ON_PLAYER_LOGIN)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void Playerlogin(OnPlayerLogin event)
	{
		L2PcInstance player = event.getActiveChar();
		if (getVip(player))
		{
			player.sendMessage("Eres player Vip.");
		}
	}
	
	public boolean getVip(L2PcInstance player)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT vip_time FROM character_vip WHERE char_id=?"))
		{
			ps.setInt(1, player.getObjectId());
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					final long vip_time = rs.getLong("vip_time");
					if (System.currentTimeMillis() < vip_time)
					{
						player.setVip(true);
						return true;
					}
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	private void load()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM character_vip WHERE vip_time<?"))
		{
			ps.setLong(1, System.currentTimeMillis());
			ps.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		new SistemaVip();
	}
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		L2Object target = activeChar.getTarget();
		L2PcInstance player = null;
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualcommand = st.nextToken();
		String val = "";
		if (st.countTokens() >= 1)
		{
			val = st.nextToken();
		}
		if (target instanceof L2PcInstance)
		{
			player = (L2PcInstance) target;
		}
		else
		{
			return false;
		}
		if (actualcommand.equalsIgnoreCase(VCOMMANDS[0]))
		{
			player.setvip(Byte.parseByte(val));
			player.sendMessage("Felicitaciones eres Vip!!");
			activeChar.sendMessage("Has dado vip al target.");
			return true;
		}
		else if (actualcommand.equalsIgnoreCase(VCOMMANDS[1]))
		{
			player.removevip();
			player.sendMessage("Vip ha sido removido.");
			activeChar.sendMessage("Has removido vip al target.");
			return true;
		}
		return false;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return VCOMMANDS;
	}
}
