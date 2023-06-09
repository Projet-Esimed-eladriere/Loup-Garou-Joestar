const { Sequelize } = require('sequelize');

exports.sequelize = new Sequelize({
    dialect: 'sqlite',
    storage: 'sql/database.sqlite'
});
