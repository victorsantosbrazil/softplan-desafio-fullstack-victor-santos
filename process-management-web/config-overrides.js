const {
  override,
  addBabelPlugins,
} = require("customize-cra");

module.exports = override( 
  (config, _) => {
    config.ignoreWarnings = [/Failed to parse source map/];
    return config;
  },
  ...addBabelPlugins(
      "babel-plugin-transform-typescript-metadata", 
  ),
);