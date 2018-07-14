<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class GroupTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('groups')->truncate();

        DB::table('groups')->insert([
            0 => [
                'name' => 'Group A',
                'level_id' => DB::table('levels')->first()->id,
            ],

            1 => [
                'name' => 'Group B',
                'level_id' => DB::table('levels')->first()->id,
            ],

            2 => [
                'name' => 'Group A',
                'level_id' => DB::table('levels')->find(2)->id,
            ],

            3 => [
                'name' => 'Group B',
                'level_id' => DB::table('levels')->find(2)->id,
            ],

            4 => [
                'name' => 'Group A',
                'level_id' => DB::table('levels')->find(3)->id,
            ],
        ]);
    }
}
