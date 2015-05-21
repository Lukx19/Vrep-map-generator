threadFunction=function()
    -- Put your thread code here (initialization and clean-up code should not be in here)
local socket = require("socket")
-- create a TCP socket and bind it to the local host, at any port
local server = assert(socket.bind("*", 9999))
-- Open a communication tube:
tubeHandle=simTubeOpen(0,'room_generation',1)
server:settimeout(10)
while (simGetSimulationState()~=sim_simulation_advancing_abouttostop) do
    simSetThreadIsFree(true)
    local client = server:accept()
    if client ~= nil then
        client:settimeout(10)
        -- make sure we don't block waiting for this client's line    
        local block, err = client:receive('*a')
        simSetThreadIsFree(false)
        -- if there was no error, send it back to the Renderer
        if not err then 
            simTubeWrite(tubeHandle,block)
        end 
          -- done with client, close the object
        client:close()
    end

   
end
end

-- Put some initialization code here:
simSetThreadSwitchTiming(2) -- Default timing for automatic thread switching

-- Here we execute the regular thread code:
res,err=xpcall(threadFunction,function(err) return debug.traceback(err) end)
if not res then
    simAddStatusbarMessage('Lua runtime error: '..err)
end

-- Put some clean-up code here:

