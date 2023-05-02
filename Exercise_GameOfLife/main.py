import pygame
import pygame_menu
import numpy as np
import tkinter
import tkinter.filedialog
from datetime import datetime

WIDTH, HEIGHT = 500, 500
CELL_SIZE = 20
ROWS, COLS = 25, 25
SPEED = 50
OLD_GRID = np.zeros((ROWS, COLS))
LIVE_COLOR = (232, 129, 232)
DEAD_COLOR = (255, 255, 255)
pygame.init()
SCREEN = pygame.display.set_mode((WIDTH, HEIGHT))


def set_old_grid(grid):
    global OLD_GRID
    OLD_GRID = grid.copy()


def set_delay(value):
    global SPEED
    SPEED = value


def main():
    open_main_menu()
    pygame.quit()


def get_world_state(grid):
    if grid.sum() == 0:
        font = pygame.font.SysFont(None, 24)
        display = font.render('Dead World', True, (230, 0, 0))

    elif (OLD_GRID == grid).all():
        font = pygame.font.SysFont(None, 24)
        display = font.render('Static World', True, (230, 230, 0))
    else:
        font = pygame.font.SysFont(None, 24)
        display = font.render('Growing World', True, (0, 230, 0))
    SCREEN.blit(display, (360, 520))


def game_loop(grid):
    running = True

    while running:

        SCREEN.fill(pygame.Color("#282828"))
        font = pygame.font.SysFont(None, 24)
        display = font.render('Press SPACE to pause/edit', True, (230, 230, 0))
        SCREEN.blit(display, (20, 520))
        set_old_grid(grid)
        grid = game_of_life(grid)
        get_world_state(grid)

        draw_grid(grid)
        pygame.display.flip()
        clock = pygame.time.Clock()
        state = pygame.key.get_pressed()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif state[pygame.K_ESCAPE]:

                open_pause_menu(grid)
            elif state[pygame.K_SPACE]:
                return grid
        clock.tick(SPEED)


def game_of_life(grid):
    new_grid = grid.copy()
    for row_index in range(ROWS):
        for col_index in range(COLS):
            alive_neighbors = count_alive_neighbors(grid, row_index, col_index)
            current_cell = grid[row_index, col_index]
            if current_cell == 1 and (alive_neighbors < 2 or alive_neighbors > 3):
                new_grid[row_index, col_index] = 0
            elif current_cell == 0 and alive_neighbors == 3:
                new_grid[row_index, col_index] = 1

    return new_grid


def count_alive_neighbors(grid, row, col):
    num_rows, num_cols = grid.shape
    return sum([
        grid[(row - 1) % num_rows, (col - 1) % num_cols],
        grid[(row - 1) % num_rows, col],
        grid[(row - 1) % num_rows, (col + 1) % num_cols],
        grid[row, (col - 1) % num_cols],
        grid[row, (col + 1) % num_cols],
        grid[(row + 1) % num_rows, (col - 1) % num_cols],
        grid[(row + 1) % num_rows, col],
        grid[(row + 1) % num_rows, (col + 1) % num_cols],
    ])


def edit_grid(grid=None):
    if grid is None:
        grid = np.zeros((ROWS, COLS))

    running = True

    while running:

        SCREEN.fill(pygame.Color("#585858"))

        font = pygame.font.SysFont(None, 24)
        start_display = font.render('Press SPACE to start', True, (0, 230, 0))

        draw_grid(grid)
        SCREEN.blit(start_display, (20, 520))
        pygame.display.flip()

        state = pygame.key.get_pressed()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.MOUSEBUTTONDOWN:
                grid = handle_mouse_click(grid)
            elif state[pygame.K_SPACE]:
                grid = game_loop(grid)
            elif state[pygame.K_ESCAPE]:
                open_pause_menu(grid)


def open_pause_menu(grid):
    screen = pygame.display.set_mode((500, 550))
    pause_menu = pygame_menu.Menu(title='Paused', theme=pygame_menu.themes.THEME_DARK, height=550, width=500)
    pause_menu.add.range_slider('Speed', SPEED, (10, 100), 1, onchange=set_delay, value_format=lambda x: str(int(x)))
    pause_menu.add.button("Resume", edit_grid, grid)
    pause_menu.add.button("Save", save, grid)
    pause_menu.add.button("Load", load)
    pause_menu.mainloop(screen)


def load():
    top = tkinter.Tk()
    top.withdraw()
    file_name = tkinter.filedialog.askopenfilename(parent=top)
    if file_name == "":
        open_main_menu()
    top.destroy()
    grid = np.load(file_name)
    edit_grid(grid)


def save(grid):
    file_name = datetime.now().strftime("GameOfLife_%d%m%y_%H%M")
    np.save(file_name, grid)


def open_main_menu():
    screen = pygame.display.set_mode((500, 550))
    main_menu = pygame_menu.Menu(title='Game of Life', theme=pygame_menu.themes.THEME_DARK, height=550, width=500)
    main_menu.add.button('Play', edit_grid)
    main_menu.add.button("Load", load)
    main_menu.add.button('Quit', pygame_menu.events.EXIT)

    main_menu.mainloop(screen)


def draw_grid(grid):
    rects = []

    for row in range(ROWS):
        for col in range(COLS):
            color = LIVE_COLOR if grid[row, col] else DEAD_COLOR
            rect = pygame.Rect(row * CELL_SIZE, col * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            if grid[row, col]:
                rects.append(pygame.draw.rect(SCREEN, color, rect))
            else:
                rects.append(pygame.draw.rect(SCREEN, color, rect, 1))


def handle_mouse_click(grid):
    mouse_pos = pygame.mouse.get_pos()
    col = mouse_pos[1] // CELL_SIZE
    row = mouse_pos[0] // CELL_SIZE
    grid[row, col] = 1 - grid[row, col]
    return grid


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()
