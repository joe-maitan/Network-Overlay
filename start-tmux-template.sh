##!/bin/sh

hosts=("albany" "tokyo" "ant" "dragonfly" "mosquito" "honolulu" "raleigh" "juneau" "lansing" "montgomery")

directory="~/CS455/csx55"
#start and detach from session
tmux new-session -d -s tmux-session-name

for i in "${!hosts[@]}"; do
    if [ "$i" -eq 0 ]; then
        #first host, just ssh
        tmux send-keys -t tmux-session-name "ssh ${hosts[$i]}" C-m "cd $directory" C-m "clear" C-m
    else
        #split the window first, then SSH
        tmux split-window -t tmux-session-name
        tmux select-layout -t tmux-session-name tiled
        tmux send-keys -t tmux-session-name "ssh ${hosts[$i]}" C-m "cd $directory" C-m "clear" C-m
    fi
done

#attach to the session
tmux attach -t tmux-session-name
