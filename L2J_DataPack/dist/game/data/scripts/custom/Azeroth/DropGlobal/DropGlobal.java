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
package custom.Azeroth.DropGlobal;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.events.EventType;
import com.l2jserver.gameserver.model.events.ListenerRegisterType;
import com.l2jserver.gameserver.model.events.annotations.RegisterEvent;
import com.l2jserver.gameserver.model.events.annotations.RegisterType;
import com.l2jserver.gameserver.model.events.impl.character.OnCreatureKill;
import com.l2jserver.util.Rnd;

/**
 * @author Eze
 */
public class DropGlobal extends AbstractNpcAI
{
	public DropGlobal()
	{
		super(DropGlobal.class.getSimpleName(), "custom");
	}
	
	@RegisterEvent(EventType.ON_CREATURE_KILL)
	@RegisterType(ListenerRegisterType.GLOBAL)
	public void playerAttack(OnCreatureKill event)
	{
		if ((event.getAttacker() instanceof L2PcInstance) && (event.getAttacker() != null) && (event.getTarget() instanceof L2Attackable))
		{
			L2PcInstance killer = (L2PcInstance) event.getAttacker();
			L2Attackable monster = (L2Attackable) event.getTarget();
			if ((monster != null) && (killer != null))
			{
				if (killer.getLevel() > ((monster.getLevel() + 7)))
				{
					return;
				}
				if (Rnd.get(100) <= 70)
				{
					killer.addItem("drop", 6392, Rnd.get(1, 3), monster, true);
				}
				if (Rnd.get(100) <= 30)
				{
					killer.addItem("drop", 6393, Rnd.get(1, 2), monster, true);
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		new DropGlobal();
	}
}
