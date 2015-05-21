-- DO NOT WRITE CODE OUTSIDE OF THE if-then-end SECTIONS BELOW!! (unless the code is a function definition)

-- Compatibility: Lua-5.1
function split(str, pat)
   print(pat)
   print (str)
   local t = {}  -- NOTE: use {n = 0} in Lua-5.0
   local fpat = "(.-)" .. pat
   local last_end = 1
   local s, e, cap = str:find(fpat, 1)
   while s do
      if s ~= 1 or cap ~= "" then
     table.insert(t,cap)
      end
      last_end = e+1
      s, e, cap = str:find(fpat, last_end)
   end
   if last_end <= #str then
      cap = str:sub(last_end)
      table.insert(t, cap)
   end
   return t
end

function addWall(x,y,type)
     if type == "-1" or type =="-2"  then
     else   
        local suffix
        local wall_handle
        if wall_sufix == -1 then suffix=""
        else suffix = wall_sufix..""
        end 
        
        if type == "0" then 
            simLoadModel(SOURCE_WALLS.."horizontal.ttm") 
        elseif type == "1" then
            simLoadModel(SOURCE_WALLS.."vertical.ttm") 
        elseif type == "2" then
            simLoadModel(SOURCE_WALLS.."horizontal_up.ttm")
        elseif type == "3" then
            simLoadModel(SOURCE_WALLS.."horizontal_down.ttm") 
        elseif type == "4" then
            simLoadModel(SOURCE_WALLS.."vertical_right.ttm")
        elseif type == "5" then
            simLoadModel(SOURCE_WALLS.."vertical_left.ttm")
        elseif type == "6" then
            simLoadModel(SOURCE_WALLS.."top_left.ttm")
        elseif type == "7" then
            simLoadModel(SOURCE_WALLS.."top_right.ttm")
        elseif type == "8" then
            simLoadModel(SOURCE_WALLS.."bottom_right.ttm")
        elseif type == "9" then
            simLoadModel(SOURCE_WALLS.."bottom_left.ttm")
        elseif type == "10" then
            simLoadModel(SOURCE_WALLS.."cross.ttm")
        end        
        
        wall_handle=simGetObjectHandle("w"..suffix)
        wall_respondables = simGetObjectsInTree(wall_handle,sim_handle_all,1)

        wall_sufix=wall_sufix+1
        local old_pos = simGetObjectPosition(wall_handle,-1)
        simSetObjectPosition(wall_handle,-1,{old_pos[1]+x,old_pos[2]+y,1})
        table.insert(map_walls,wall_handle)
        for key,val in ipairs(wall_respondables) do
            table.insert(map_respondables,val)
        end


    end
end

if (sim_call_type==sim_childscriptcall_initialization) then

   SOURCE_WALLS="/home/lukas/workspace/java/MFF/Vrep-map-generator/vrep/walls/"
   SOURCE_MAP="/home/lukas/workspace/java/MFF/Vrep-map-generator/vrep/maps/"
   tubeHandle=simTubeOpen(0,'room_generation',1)
   map_num =0


end


if (sim_call_type==sim_childscriptcall_actuation) then
end


if (sim_call_type==sim_childscriptcall_sensing) then
     local x=0
     local y=0     
     map_walls={}
     map_respondables = {}
     wall_sufix=-1;
     s,r,w=simTubeStatus(tubeHandle)
     if s == 1 and r>0 then
        if map_num >0 then
            respondables = simGetObjectsInTree(wall,sim_handle_all,1)
            for key,value in ipairs(respondables) do
                simRemoveObject(value)
            end
            simRemoveObject(wall)
        end
        
        local block = simTubeRead(tubeHandle)
         if block ~= nil then
            print("in for cyclus")
            print(block)
            lines=split(block,"\n")
            for key,line in ipairs(lines) do
                fields = split(line,":")
                for key2,field in ipairs(fields) do
                    addWall(x,y,field)
                    x=x+1
                end
                x=0
                y=y+1
            end
            wall=simGroupShapes(map_walls)
            for key,val in ipairs(map_respondables) do
                simSetObjectParent(val,wall,true)
            end
            simSetObjectPosition(wall,-1,{0,0,1})
            simSaveModel(wall,SOURCE_MAP.."map"..map_num..".ttm")
            map_num=map_num+1
        end
     end
end


if (sim_call_type==sim_childscriptcall_cleanup) then

end