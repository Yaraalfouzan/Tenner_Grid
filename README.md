# tenner grid 
Our solution to the Tenner Grid problem, employs diverse
techniques including backtracking, forward checking, and
the minimum remaining values (MRV) heuristic with
forward checking to solve the grid . Initially, an initial state is
generated randomly adhering to the problem's constraints.
The backtracking solver method recursively explores
potential solutions, trying different numbers for each empty
cell and backtracking when necessary. The forward checking
solver enhances this approach by maintaining domains of
valid values for each cell and updating them as values are
assigned, thereby reducing the search space. Additionally,
the MRV heuristic with forward checking selects empty cells
with the fewest remaining possible values, potentially
expediting the solution process. Throughout the solving
process, the code tracks the number of consistency checks
and variable assignments. Finally, upon finding a solution or
determining its absence, our code presents the solved grid
along with relevant performance metrics for each solving
technique.
