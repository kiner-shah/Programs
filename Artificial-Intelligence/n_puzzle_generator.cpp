#include <algorithm>
#include <vector>
#include <random>
#include <iostream>
#include <iomanip>

class NPuzzleGenerator
{
    int m_n;
    std::vector<int> m_board_elements;

    void fisher_yates_shuffle();
    int count_inversions() const;
    void switch_tiles();

public:
    NPuzzleGenerator(int n = 3);
    std::vector<int> get_board() const;
    int get_n() const;
    void print_board() const;
};

NPuzzleGenerator::NPuzzleGenerator(int n)
    : m_n(n), m_board_elements(n * n, 0)
{
    std::iota(m_board_elements.begin(), m_board_elements.end(), 1);
    m_board_elements[m_board_elements.size() - 1] = 0;  // 0 means empty slot
    fisher_yates_shuffle();
    int inversion_count = count_inversions();
    if (inversion_count % 2 != 0)
    {
        switch_tiles();
    }
}

void NPuzzleGenerator::fisher_yates_shuffle()
{
    std::random_device rd;
    std::mt19937 generator(rd());

    for (int i = m_board_elements.size() - 1; i >= 0; i--)
    {
        std::uniform_int_distribution<> distribution(0, i);
        int index_to_swap_with = distribution(generator);
        std::swap(m_board_elements[i], m_board_elements[index_to_swap_with]);
    }
}

int NPuzzleGenerator::count_inversions() const
{
    int total_inversions = 0;
    for (int i = 0; i < m_board_elements.size(); i++)
    {
        int inversion_count_for_i = 0;
        for (int j = i+1; j < m_board_elements.size(); j++)
        {
            if (m_board_elements[j] != 0 && m_board_elements[j] < m_board_elements[i])
            {
                inversion_count_for_i++;
            }
        }
        total_inversions += inversion_count_for_i;
    }
    return total_inversions;
}

void NPuzzleGenerator::switch_tiles()
{
    int i = 0;

    while (m_board_elements[i] == 0 || m_board_elements[i+1] == 0)
    {
        i++;
    }

    std::swap(m_board_elements[i], m_board_elements[i+1]);
}

std::vector<int> NPuzzleGenerator::get_board() const
{
    return m_board_elements;
}

int NPuzzleGenerator::get_n() const
{
    return m_n;
}

void NPuzzleGenerator::print_board() const
{
    const auto& board = m_board_elements;
    int n = m_n;
    for (int i = 0; i < board.size(); i++)
    {
        std::cout << std::setw(3) << board[i];
        if (i % n == n - 1)
        {
            std::cout << '\n';
        }
    }
}

int main()
{
    NPuzzleGenerator generator(8);
    generator.print_board();
}
