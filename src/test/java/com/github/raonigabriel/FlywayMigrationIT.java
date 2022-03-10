package com.github.raonigabriel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;
import org.testcontainers.utility.DockerImageName;

/**
 * @author raonigabriel
 *
 */
public class FlywayMigrationIT {

	private static final int SCRIPT_COUNT = 2;
	
	private static String schemaVersion;

	@BeforeClass
	public static void setup() throws IOException {
		schemaVersion = resourceToString("/db/migration/schema-version");
	}

	@Test
	public void given_flyway_with_empty_db_when_migrate_then_success() {
		final DockerImageName imageName = dockerImage("postgres:14-alpine3.15");

		try (TestContainer emptyDatabase = new TestContainer(imageName)) {

			// Given
			final Flyway flyway = Flyway.configure()
					.dataSource(emptyDatabase.getDataSource())
					.load();

			assertNotNull(flyway);

			// When
			final MigrateResult result = flyway.migrate();

			// Then
			assertTrue(result.success);
			assertEquals(schemaVersion, result.targetSchemaVersion);
			assertEquals(SCRIPT_COUNT, result.migrationsExecuted);
		}

	}

	@Test
	public void given_flyway_with_partial_updated_db_when_migrate_then_success() throws IOException, SQLException {

		final DockerImageName imageName = dockerImage("postgres:14-alpine3.15");

		try (TestContainer database = new TestContainer(imageName)) {

			// Partially update the database by executing just the first migration
			final String sql = resourceToString("/db/migration/V1_0_0__INITIAL_STRUCTURE.sql");
			database.executeUpdate(sql);

			// Given
			final Flyway flyway = Flyway.configure()
					.dataSource(database.getDataSource())
					.baselineOnMigrate(true)
					.baselineVersion("1_0_0")					
					.load();

			assertNotNull(flyway);

			// When
			final MigrateResult result = flyway.migrate();

			// Then
			assertTrue(result.success);
			assertEquals("1.0.0", result.initialSchemaVersion);
			assertEquals(schemaVersion, result.targetSchemaVersion);
			assertEquals(SCRIPT_COUNT - 1, result.migrationsExecuted);
		}

	}

	@Test
	public void given_flyway_with_full_updated_db_when_migrate_then_success() throws IOException {

		final DockerImageName imageName = dockerImage(resourceToString("/dockerImageName"))
				.asCompatibleSubstituteFor("postgres");

		try (TestContainer fullUpdatedDatabase = new TestContainer(imageName)) {

			// Given
			final Flyway flyway = Flyway.configure()
					.dataSource(fullUpdatedDatabase.getDataSource())
					.baselineOnMigrate(true)
					.baselineVersion(schemaVersion.replace('.', '_'))
					.load();

			assertNotNull(flyway);

			// When
			final MigrateResult result = flyway.migrate();

			// Then
			assertTrue(result.success);
			assertEquals(schemaVersion, result.initialSchemaVersion);
			assertEquals(0, result.migrationsExecuted);
		}

	}

	private static String resourceToString(final String fileName) throws IOException {
		return IOUtils.toString(IOUtils.resourceToByteArray(fileName), "UTF-8");
	}

	private DockerImageName dockerImage(final String imageName) {
		return DockerImageName.parse(imageName);
	}

}
