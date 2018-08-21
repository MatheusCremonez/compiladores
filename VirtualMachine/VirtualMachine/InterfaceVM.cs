﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Collections;

namespace VirtualMachine
{
    public partial class InterfaceVM : Form
    {
        public InterfaceVM()
        {
            InitializeComponent();
        }

        //Carrega o Arquivo e adiciona ele no dataGrid
        private void arquivoToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Variaveis Gerais para esta etapa
            int i, linhaNumero = 1;
            string newline;

            //Pega o arquivo
            OpenFileDialog openFile = new OpenFileDialog();

            //Verifica se o arquivo foi selecionado corretamente
            if (openFile.ShowDialog() == DialogResult.OK)
            {
                //Cria a dataTable para adicionarmos os valores na lista
                DataTable dt = new DataTable();

                //Adiciona a tabela ano dataGrid
                dataGridView1.DataSource = dt;

                //Colunas da tabela
                dt.Columns.Add("I");
                dt.Columns.Add("Instrução");
                dt.Columns.Add("Atributo #1");
                dt.Columns.Add("Atributo #2");

                //Preenche as colunas para caber no espaço do dataGrid
                dataGridView1.Columns[0].AutoSizeMode = DataGridViewAutoSizeColumnMode.DisplayedCells;
                dataGridView1.Columns[1].AutoSizeMode = DataGridViewAutoSizeColumnMode.DisplayedCells;
                dataGridView1.Columns[2].AutoSizeMode = DataGridViewAutoSizeColumnMode.DisplayedCells;
                dataGridView1.Columns[3].AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

                //Coloca o arquivo em uma variavel
                StreamReader file = new StreamReader(openFile.FileName);

                //Enquanto a tiver linha no arquivo le
                while ((newline = file.ReadLine()) != null)
                {
                    //Cria uma linha para a tabela
                    DataRow dr = dt.NewRow();

                    //Vetor para pegar a linha, separando por espaço
                    string[] values;
                    values = newline.Split(' ');
                    
                    //Adiciona os valores separados por espaço nas colunas
                    for (i = 0; i < values.Length; i++)
                    {
                        dr[0] = linhaNumero;
                        dr[i+1] = values[i];
                    }
                    //Adiciona a linha na tabela
                    dt.Rows.Add(dr);
                    linhaNumero++;
                }

                file.Close();

            }

        }

        //Executa o código direto, sem passar linha a linha
        private void startToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Cria a dataTable para adicionarmos colunas e linhas na lista
            DataTable dt = new DataTable();
            //Coloca no dataGrid as colunas criadas
            dataGridView2.DataSource = dt;

            //Colunas da tabela
            dt.Columns.Add("Endereço(s):");
            dt.Columns.Add("Valor:");

            //Preenche as colunas para caber no espaço do dataGrid
            dataGridView2.Columns[0].AutoSizeMode = DataGridViewAutoSizeColumnMode.DisplayedCells;
            dataGridView2.Columns[1].AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            //Linhas da tabela
            DataRow dr = dt.NewRow();

            //Variaveis Gerais
            int linhaInstrucao = 0, atributo = 1, endereco = 0;
            int topoDaPilha = -1;

            //Array que guarda os valores da pilha e posição
            ArrayList arrayStash = new ArrayList();
            //Objeto da Classe que contém as instruções que o Compilador faz
            Instruction instruction = new Instruction();


            while (linhaInstrucao < dataGridView1.Rows.Count)
            {
                topoDaPilha = instruction.execute(dataGridView1.Rows[linhaInstrucao].Cells[atributo].Value.ToString(), dataGridView1.Rows[linhaInstrucao].Cells[atributo + 1].Value.ToString(), dataGridView1.Rows[linhaInstrucao].Cells[atributo + 2].Value.ToString(), arrayStash, topoDaPilha);
                endereco = arrayStash.Count - 1;

                dr[0] = endereco.ToString();
                dr[1] = arrayStash[topoDaPilha].ToString();
                

                linhaInstrucao++;
            }
            dt.Rows.Add(dr);
        }
        
        private void startStepToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Executa o código linha por linha carregado
        }
    }

    public class Instruction
    {
        public int execute(string instruction, string firstAttribute, string secondAttribute, ArrayList array, int topoPilha)
        {

            if (String.IsNullOrEmpty(instruction))
            {
                throw new Exception("Instruction not provided");
            }
            else
            {
                switch (instruction)
                {
                    case "LDC":
                        array.Add(firstAttribute);
                        return topoPilha + 1;

                    case "ADD":
                        int x = Convert.ToInt32(array[topoPilha]);
                        int y = Convert.ToInt32(array[topoPilha - 1]);
                        array[topoPilha - 1] = x + y;
                        array.RemoveAt(topoPilha);
                        return topoPilha - 1;

                    default:
                        return topoPilha;

                }
            }
        }
    }
}
