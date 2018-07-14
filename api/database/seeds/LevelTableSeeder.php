<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class LevelTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('levels')->truncate();

        DB::table('levels')->insert([
            0 => [
                'name' => 'Level 1',
                'department_id' => DB::table('departments')->first()->id,
            ],

            1 => [
                'name' => 'Level 2',
                'department_id' => DB::table('departments')->first()->id,
            ],

            2 => [
                'name' => 'Level 3',
                'department_id' => DB::table('departments')->first()->id,
            ],

            3 => [
                'name' => 'Level 4',
                'department_id' => DB::table('departments')->first()->id,
            ]
        ]);
    }
}
